import React, { lazy } from 'react'
import Loadable from 'app/components/Loadable/Loadable';

const AppList = Loadable(lazy(() => import("./List")));
const AppManagement = Loadable(lazy(() => import("./Management")));

const PromotionRoutes = [
    {
        path: '/admin/promotion/list',
        element: <AppList />,
    },
    {
        path: '/admin/promotion/management/:id',
        element: <AppManagement />,
    },
]

export default PromotionRoutes
