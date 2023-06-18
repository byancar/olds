# -*- coding: utf-8 -*-

@auth.requires_login()
def index():
    a = auth.user
    addresses = db(db.address.user_id == auth.user.id).select()

    return dict(var=a,addresses = addresses)

@auth.requires_login()
def orders():
    return locals()

@auth.requires_login()
def address():
    pass

@auth.requires_login()
def select_address():
    addresses = db(db.address.user_id == auth.user.id).select()

    adrs = []
    for address in addresses:
        adrs.append(address.address+', '+str(address.adr_number))

    form = SQLFORM.factory(
        Field('address', requires=IS_IN_SET(adrs))
        )
    if form.accepts(request.vars):
        session.address = form.vars.address
        redirect(URL('default', 'buy'))

    button = A(T('New address'), _href=URL('create_address'))
    return dict(addresses=addresses, button=button, form=form)

@auth.requires_login()
def create_address():
    now = datetime.datetime.now()
    span = datetime.timedelta(days=100)
    product_list = db(db.product.created_on >= (now-span)).select(limitby=(0,6), orderby=~db.product.created_on)



    db.address.user_id.default = auth.user.id
    db.address.user_id.readable = db.address.user_id.writable = False

    form = SQLFORM(db.address)
    if form.process().accepted:
        if session.cart and session.balance != 0:
            redirect(URL('customer', 'select_address'))
        else:
            redirect(URL('customer', 'index'))
    return dict(form=form)

def user():
    """
    exposes:
    http://..../[app]/default/user/login
    http://..../[app]/default/user/logout
    http://..../[app]/default/user/register
    http://..../[app]/default/user/profile
    http://..../[app]/default/user/retrieve_password
    http://..../[app]/default/user/change_password
    http://..../[app]/default/user/manage_users (requires membership in
    use @auth.requires_login()
        @auth.requires_membership('group name')
        @auth.requires_permission('read','table name',record_id)
    to decorate functions that need access control
    """
    return dict(form=auth())

@cache.action()
def download():
    """
    allows downloading of uploaded files
    http://..../[app]/default/download/[filename]
    """
    return response.download(request, db)
