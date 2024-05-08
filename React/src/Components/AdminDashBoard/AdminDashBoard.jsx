import React, { useEffect, useState } from 'react'
import Loader from '../Loader/Loader'
import axios from 'axios'
import { useQuery } from 'react-query'
import toast from 'react-hot-toast'
import Lottie from 'lottie-react'
import img from '../Cart/Animation - 1709313300851.json'
import { Link } from 'react-router-dom'

export default function AdminDashBoard() {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');


    function getAllProducts() {
        const headers = {
            'Authorization': `Bearer ${token}`
        };
        return axios.get("http://localhost:8080/api/v1/admin/get-all-products", { headers });
    }

    const { data, isLoading } = useQuery('getAllProducts', getAllProducts);


    useEffect(() => {
        getAllProducts();
    }, [])

    // console.log(data.data);



    return <>
        <div className="container">
            <div className="text-center mt-3">
                <h2 className='mb-3'>Admin Page</h2>
                <Link aria-current="page" to={'/AddProduct'}>
                    <button className='btn btn-success me-3 p-3'>Add New Product</button>
                </Link>
                {data?.data ? <div className="row">
                    <div className="col-md-12">
                        <div className="container w-75 mt-5">
                            <div className='row'>
                                {data.data.map((product, idx) => <div className="col-md-6" key={idx}>
                                    <div className='row'>
                                        <div className='col-md-6'>
                                            <h5 className=''>{product.name}</h5>
                                        </div>
                                        <div className='col-md-6'>
                                            <img src={`${product.thumbNail}`} alt="" className='w-50 mb-3' />
                                        </div>
                                    </div>
                                </div>
                                )}
                                <hr className='mt-2' />
                            </div>
                        </div>
                        {/* <div className='d-flex flex-column align-items-center'>
                            <div className="col-md-5 col-12">
                                <div className='text-center'>
                                    <Lottie animationData={img}></Lottie>
                                </div>
                            </div>
                        </div> */}
                    </div>
                </div>
                    : <div className='d-flex flex-column align-items-center'>
                        <div className="col-md-5 col-12">
                            <div className='text-center'>
                                <Lottie animationData={img}></Lottie>
                            </div>
                        </div>
                    </div>}
            </div>
        </div>
    </>
}