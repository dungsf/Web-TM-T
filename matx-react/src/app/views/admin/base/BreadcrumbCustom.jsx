import React from 'react'
import { Breadcrumb } from 'app/components'

const BreadcrumbCustom = ({id,name}) => {
    if(id === 'add'){
        return (
            <Breadcrumb
                routeSegments={[
                    { name: name},
                    { name:  'Tạo mới'},
                ]}
            />
        )
    }else{
        return (
            <Breadcrumb
                routeSegments={[
                    { name: name},
                    { name: 'Cập nhật'},
                    { name: id},
                ]}
            />
        )
    }
}

export default BreadcrumbCustom