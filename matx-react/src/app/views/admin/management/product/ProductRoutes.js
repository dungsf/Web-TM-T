import React, { lazy } from 'react'
import Loadable from 'app/components/Loadable/Loadable';

const AppList = Loadable(lazy(() => import("./List")));
const AppManagement = Loadable(lazy(() => import("./Management")));

const materialRoutes = [
    {
        path: '/admin/product/list',
        element: <AppList />,
    },
    {
        path: '/admin/product/management/:id',
        element: <AppManagement />,
    },
]

export default materialRoutes
