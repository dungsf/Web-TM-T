import React, { lazy } from 'react'
import Loadable from 'app/components/Loadable/Loadable';

const AppList = Loadable(lazy(() => import("./List")));
const AppManagement = Loadable(lazy(() => import("./Management")));

const colorRoutes = [
    {
        path: '/admin/color/list',
        element: <AppList />,
    },
    {
        path: '/admin/color/management/:id',
        element: <AppManagement />,
    },
]

export default colorRoutes
