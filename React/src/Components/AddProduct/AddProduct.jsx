import axios from 'axios';
import React from 'react'
import { useFormik } from 'formik'
import * as yup from 'yup'
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { ColorRing } from 'react-loader-spinner';
import { useQuery } from 'react-query';
import Loader from '../Loader/Loader';
import toast from 'react-hot-toast';

export default function AddProduct() {

    let naviagte = useNavigate();
    const token = localStorage.getItem('token');


    async function addProduct(body) {
        const headers = {
            'Authorization': `Bearer ${token}`
        };
        setStillLoading(true)
        axios.post(`http://localhost:8080/api/v1/admin/add-product`, body, { headers })
            .then(function (x) {
                setStillLoading(false);
                setIsSuccess(true);
                setTimeout(function () {
                    setIsSuccess(false)
                    naviagte('/AdminDashboard');
                }, 2500);
            })
            .catch(function (x) {
                setStillLoading(false);
                console.log(x);
                setTimeout(function () {
                    setIsFailed(undefined);
                }, 3000);
            })
    }


    const validateSchema = yup.object({
        "name": yup.string().required("name is required"),
        "description": yup.string().required("Description is required"),
        "price": yup.number().required("Price is required"),
        "rating": yup.number().min(1, "Rating Average is between 1 and 5").max(5, "Rating Average is between 1 and 5").required("Rating Average is required"),
        "quantityLeft": yup.number().required("quantityLeft is required"),
        "thumbNail": yup.string().required("Image Cover is required"),
    })


    const registerForm = useFormik({
        initialValues: {
            "name": "",
            "description": "",
            "price": "",
            "rating": "",
            "quantityLeft": "",
            "thumbNail": "",
        },

        validationSchema: validateSchema,
        onSubmit: addProduct
    })


    const [isSuccess, setIsSuccess] = useState(false);
    const [isFailed, setIsFailed] = useState(undefined);
    const [stillLoading, setStillLoading] = useState(false);

    // console.log(data.data.data);

    return <>
        <div className="container mt-4 w-50">

            {isSuccess ? toast.success('Product added successfully', { duration: 2000, position: 'top-center' }) : null}
            {isFailed ? toast.error('Error occured', { duration: 2000, position: 'top-center' }) : null}

            <h3 className='mb-3'>Add Product</h3>
            <form onSubmit={registerForm.handleSubmit}>
                <div className="mb-2">
                    <label htmlFor="name" className="form-label">name</label>
                    <input type="text" className="form-control" id="name" value={registerForm.values.name} onChange={registerForm.handleChange} onBlur={registerForm.handleBlur} />
                    {registerForm.errors.name && registerForm.touched.name ? <div className='alert alert-danger mt-2'>{registerForm.errors.name}</div> : null}
                </div>

                <div className="mb-2">
                    <label htmlFor="description" className="form-label">Description</label>
                    <textarea className="form-control" id="description" value={registerForm.values.description} onChange={registerForm.handleChange} onBlur={registerForm.handleBlur} rows="3"></textarea>
                    {registerForm.errors.description && registerForm.touched.description ? <div className='alert alert-danger mt-2'>{registerForm.errors.description}</div> : null}
                </div>
                <div className="mb-2">
                    <label htmlFor="price" className="form-label">Price</label>
                    <input type="number" className="form-control" id="price" value={registerForm.values.price} onChange={registerForm.handleChange} onBlur={registerForm.handleBlur} />
                    {registerForm.errors.price && registerForm.touched.price ? <div className='alert alert-danger mt-2'>{registerForm.errors.price}</div> : null}

                </div>
                <div className="mb-2">
                    <label htmlFor="quantityLeft" className="form-label">quantityLeft</label>
                    <input type="number" className="form-control" id="quantityLeft" value={registerForm.values.quantityLeft} onChange={registerForm.handleChange} onBlur={registerForm.handleBlur} />
                    {registerForm.errors.quantityLeft && registerForm.touched.quantityLeft ? <div className='alert alert-danger mt-2'>{registerForm.errors.quantityLeft}</div> : null}

                </div>
                <div className="mb-2">
                    <label htmlFor="thumbNail" className="form-label">Image Cover Link</label>
                    <input type="text" className="form-control" id="thumbNail" value={registerForm.values.thumbNail} onChange={registerForm.handleChange} onBlur={registerForm.handleBlur} />
                    {registerForm.errors.thumbNail && registerForm.touched.thumbNail ? <div className='alert alert-danger mt-2'>{registerForm.errors.thumbNail}</div> : null}

                </div>
                <div className="mb-2">
                    <label htmlFor="rating" className="form-label">Rating Average</label>
                    <input type="number" className="form-control" id="rating" value={registerForm.values.rating} onChange={registerForm.handleChange} onBlur={registerForm.handleBlur} />
                    {registerForm.errors.rating && registerForm.touched.rating ? <div className='alert alert-danger mt-2'>{registerForm.errors.rating}</div> : null}

                </div>
                <div className=''>
                    <button className='btn bg-main mb-5 text-white d-block ms-auto' type='submit' disabled={!(registerForm.isValid && registerForm.dirty)}>
                        {stillLoading ? <ColorRing
                            visible={true}
                            height="40"
                            width="40"
                            ariaLabel="color-ring-loading"
                            wrapperStyle={{}}
                            wrapperclassName="color-ring-wrapper"
                            colors={['white', 'white', 'white', 'white', 'white']}
                        /> : "Add Product"}
                    </button>
                </div>
            </form>

        </div>
    </>
}
