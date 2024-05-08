import React, { useContext } from 'react'
import { useQuery } from 'react-query';
import { Navigate, useNavigate, useParams } from 'react-router-dom'
import Loader from '../Loader/Loader';
import axios from 'axios';
import ProductsSlider from '../ProductsSlider/ProductsSlider';
import toast from 'react-hot-toast';
export default function ProductDetails() {

    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');

    const { id } = useParams();

    function getProductDetails() {
        const headers = {
            'Authorization': `Bearer ${token}`
        };
        return axios.get(`//localhost:8080/api/v1/admin/get-specific-product/${id}`, { headers });
    }

    const { isLoading, data, isError } = useQuery(`productDetails+${id}`, getProductDetails)

    if (isLoading) {
        return <Loader />
    }

    if (isError) {
        return <Navigate to='/notFound' />
    }

    function addProductTCart(productId) {
        const headers = {
            'Authorization': `Bearer ${token}`
        };
        const url = `http://localhost:8080/cart/addToCart/${productId}/${userId}`;
        axios.post(url, null, { headers })
            .then((res) => {
                toast.success("Product added to cart successfully", { duration: 1500, position: "top-center" });
                console.log(res);
            })
            .catch((err) => {
                toast.error("Error Occured", { duration: 1500, position: "top-center" });
                console.log(err);
            });
    }

    console.log(data);

    // const productData = data.data;

    async function addProduct(id) {
        await addProductTCart(id);
    }

    return <>
        <div className="container">
            {data?.data && <div className="row align-items-center">
                <div className="col-md-3">
                    <figure>
                        <img src={data.data.thumbNail} alt={data.data.name} className='w-100' />
                    </figure>
                </div>
                <div className="col-md-9">
                    <figcaption>
                        <div>
                            <div>
                                <h2>{data.data.name}</h2>
                                <p>{data.data.description}</p>
                            </div>
                            <div className='d-flex'>
                                <p><button className='btn btn-success mt-3 me-3'><i class="fa-solid fa-sack-dollar pe-1"></i>Price : {data.data.price} LE </button></p>
                                <p><button className='btn btn-danger mt-3'><i className="fa-solid fa-star pe-1" style={{ color: '#FFD43B' }} />Rating : {data.data.rating} </button></p>
                            </div>
                        </div>
                        <button className='btn btn-success w-100 mb-5' onClick={() => addProductTCart(data.data.id)}>Add to cart <i class="fa-brands fa-shopify ps-1"></i></button>
                    </figcaption>
                </div>
            </div>}
            <ProductsSlider />
        </div>
    </>
}
