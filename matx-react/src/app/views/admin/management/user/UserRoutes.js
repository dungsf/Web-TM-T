import React, { lazy } from 'react'
import Loadable from 'app/components/Loadable/Loadable';

const AppList = Loadable(lazy(() => import("./List")));
const AppManagement = Loadable(lazy(() => import("./Management")));

const UserRoutes = [
    {
        path: '/admin/user/list',
        element: <AppList />,
    },
    {
        path: '/admin/user/management/:id',
        element: <AppManagement />,
    },
]

export default UserRoutes
