import React, { lazy } from 'react'
import Loadable from 'app/components/Loadable/Loadable';

const AppList = Loadable(lazy(() => import("./List")));
const AppManagement = Loadable(lazy(() => import("./Management")));

const Routes = [
    {
        path: '/admin/category/list',
        element: <AppList />,
    },
    {
        path: '/admin/category/management/:id',
        element: <AppManagement />,
    },
]

export default Routes;
