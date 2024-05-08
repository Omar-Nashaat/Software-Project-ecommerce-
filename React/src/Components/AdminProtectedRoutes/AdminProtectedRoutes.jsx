import React from 'react'
import toast from 'react-hot-toast';
import { Navigate } from 'react-router-dom'
import { jwtDecode } from 'jwt-decode';

export default function AdminProtectedRoutes({children}) {
    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");

    if (token) {
        if (role === 'Admin') {
            return children
        }
        localStorage.removeItem('token');
        return  <Navigate to={'/login'} />
    } else {
        return <Navigate to={'/login'}/>
    }
}