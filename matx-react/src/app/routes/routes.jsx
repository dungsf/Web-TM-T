import NotFound from 'app/views/sessions/NotFound'
import statisticalRoute from 'app/views/admin/dashboard/statistical/StatisticalRoutes'
import sessionRoutes from 'app/views/sessions/SessionRoutes'
import MatxLayout from '../components/MatxLayout/MatxLayout'

import homeAdminRoute from 'app/views/admin/dashboard/home/HomeRoutes'
import productAdminRoute from 'app/views/admin/management/product/ProductRoutes'
import promotionAdminRoute from 'app/views/admin/management/promotion/PromotionRoutes'
import categoryAdminRoute from 'app/views/admin/management/category/CategorytRoutes'
import colorAdminRoute from 'app/views/admin/management/color/ColorRoutes'
import userAdminRoute from 'app/views/admin/management/user/UserRoutes'

import containerRoute from 'app/views/user/container/ContainerRoutes'
import orderAdminRoute from 'app/views/admin/management/order/OrderRoutes'
export const AllPages = () => {
    const all_routes = [
        {
            path: '/admin',
            element: <MatxLayout />,
            children: [
                ...statisticalRoute,
                ...homeAdminRoute,
                ...colorAdminRoute,
                ...categoryAdminRoute,
                ...userAdminRoute,
                ...productAdminRoute,
                ...orderAdminRoute,
                ...promotionAdminRoute,
            ],
        },
        ...sessionRoutes,
        ...containerRoute,
        {
            path: '*',
            element: <NotFound />,
        },
    ]

    return all_routes
}
