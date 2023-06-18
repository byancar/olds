# -*- coding: utf-8 -*-
def index():
    redirect(URL(c='default', f='index'))

def show_products():
    if request.vars.category:
        product_list = db(db.product.default_category == request.vars.category).select(limitby=(0,20),
                                                                            orderby=db.product.name)
    else:
        product_list = db(db.product).select(limitby=(0,20), orderby=~db.product.name)
    return locals()
	
def view():
	if not request.args: redirect(URL(c='default',f='index'))
	product = db(db.product.id == int(request.args(0))).select().first()  
	return dict(product=product)

def buy():
    if not request.args: redirect(URL(c='default',f='index'))
    try:
        int(request.args(0))
    except ValueError:
        raise HTTP(404, 'Product not found. Invalid ID.')

    product = db(db.product.id == int(request.args(0))).select().first()
		
   
    for prod in session.cart:
        if prod[0] == product.id:
            prod[1] += 1
           
        else:
            session.cart.append([product.id, 1])
           
    else:
        session.cart.append([product.id, 1])
    redirect(URL(c='default',f='cart'))
	
    return dict(product=product, s = session.cart)

@cache.action()
def download():
    """
    allows downloading of uploaded files
    http://..../[app]/default/download/[filename]
    """
    return response.download(request, db)
