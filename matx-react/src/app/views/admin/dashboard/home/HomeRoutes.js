import React, { lazy } from 'react'
import Loadable from 'app/components/Loadable/Loadable'


const Home = Loadable(lazy(() => import('./Home')))

const homeRoutes = [
    {
        path: '/admin',
        element: <Home />,
    },
]

export default homeRoutes
