# -*- coding: utf-8 -*-

if not session.cart:
    # instantiate new cart
    session.cart, session.balance = [], 0

def contact():
  return dict()

def remove_from_cart():
    del session.cart[int(request.args(0))]
    redirect(URL(c='default',f='cart'))

def cart():
    now = datetime.datetime.now()
    span = datetime.timedelta(days=100)
    #product_list = db(db.product.created_on >= (now-span)).select(limitby=(0,3), orderby=~db.product.created_on)
    
    order = []
    balance = 0
    for product_id, qty in session.cart:
        product = db(db.product.id == product_id).select().first()
        total_price = qty*product.price
        order.append((product_id, qty, total_price, product))
        balance += total_price
    session.balance = balance

    button1 = A(T('Continue shopping'), _href=URL('default', 'index'))
    button2 = A(T('Buy'), _href=URL('select_address'))
    return dict(order=order, balance=balance)
   	
def index():
    now = datetime.datetime.now()
    span = datetime.timedelta(days=100)
    if request.vars.category:
        product_list = db(db.product.default_category == request.vars.category).select(limitby=(0,20),
                                                                            orderby=db.product.name)
    else:
        product_list = db(db.product).select(limitby=(0,20), orderby=~db.product.name)   
    return locals()

def search():
    now = datetime.datetime.now()
    span = datetime.timedelta(days=100)
    product_list = db(db.product.description.like("%"+request.vars.word+"%")).select()
    return locals()

def pages():
    content = db(db.static_pages.name == request.vars.name).select().first()  
    return dict(content=content);

@auth.requires_login()
def checkout():
    db.pending_transaction.user_id.default = auth.user.id
    db.pending_transaction.user_id.readable = db.pending_transaction.user_id.writable = False
    db.pending_transaction.confirmed.readable = db.pending_transaction.confirmed.writable = False

    pending = dict(user_id=auth.user.id,
                    products=session.cart,
                    ammount=session.balance)

    pending_record  = db.pending_transaction.insert(**pending)
    pending['id']   = pending_record
    session.pending = pending
    #redirect(URL('default', 'paypal'))
    return locals()
	
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

def register():
    form = auth.register()
    return locals()


@cache.action()
def download():
    """
    allows downloading of uploaded files
    http://..../[app]/default/download/[filename]
    """
    return response.download(request, db)
