import React from 'react'
import { Redirect } from 'react-router-dom'
import statisticalRoutes from './views/admin/dashboard/statistical/StatisticalRoutes'  

import homeAdminRoute from './views/admin/dashboard/home/HomeRoutes'
import productAdminRoute from './views/admin/management/product/ProductRoutes'
import promotionAdminRoute from './views/admin/management/promotion/PromotionRoutes'
import colorAdminRoute from 'app/views/admin/management/color/ColorRoutes'
import categoryAdminRoute from './views/admin/management/category/CategorytRoutes'
import orderAdminRoute from './views/admin/management/order/OrderRoutes'
import containerRoute from './views/user/container/ContainerRoutes'

const redirectRoute = [
    {
        path: '/admin/home',
        exact: true,
        component: () => <Redirect to="/admin/home" />,
    },
]

const errorRoute = [
    {
        component: () => <Redirect to="/session/404" />,
    },
]

const routes = [
    ...homeAdminRoute,
    ...categoryAdminRoute,
    ...colorAdminRoute,
    ...productAdminRoute,
    ...promotionAdminRoute,
    ...orderAdminRoute,
    ...containerRoute,
    ...statisticalRoutes,
    ...redirectRoute,
    ...errorRoute
]

export default routes
