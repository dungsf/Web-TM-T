import React, { lazy } from 'react'
import Loadable from 'app/components/Loadable/Loadable';

const AppList = Loadable(lazy(() => import("./List")));
const AppOrderDetail = Loadable(lazy(() => import("./Detail")));
const OrderRoutes = [
    {
        path: '/admin/order/list',
        element: <AppList />,
    },
    {
        path: '/admin/order/detail/:id',
        element: < AppOrderDetail />,
    },
    
]

export default OrderRoutes;
