import React, { lazy } from 'react'
import Loadable from 'app/components/Loadable/Loadable'

const Statistical = Loadable(lazy(() => import('./Statistical')))

const statisticalRoutes = [
    {
        path: '/admin/statistical',
        element: <Statistical />,
    },
]

export default statisticalRoutes
