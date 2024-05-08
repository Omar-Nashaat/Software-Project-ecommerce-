import axios from 'axios'
import React, { useContext, useState } from 'react'
import toast from 'react-hot-toast';
import { useNavigate } from 'react-router-dom';
import { counterContext } from '../../Context/product';
import MyModal from "../CreditCard/CreditCardForm";
import 'bootstrap/dist/css/bootstrap.min.css';
import { tokenContext } from '../../Context/token';


export default function Payment() {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    const [productId, setProductId] = useState('');
    const [productQuantity, setAllProductQuantity] = useState('');
    const nav = useNavigate();


    async function getUserCart() {
        const headers = {
            'Authorization': `Bearer ${token}`
        };
        const url = `http://localhost:8080/cart/getCart/${userId}`
        await axios.get(url, { headers })
            .then((res) => {
                // setAllProducts(res.data);
                setProductId(res.data[0].id);
                setAllProductQuantity(res.data[0].quantityLeft)
            })
            .catch((err) => {
                console.log(err);
            });
    }

    async function clearCart() {
        try {
            const headers = {
                'Authorization': `Bearer ${token}`
            }
            const res = await axios.delete("http://localhost:8080/cart/clearCart", { headers });
        } catch (err) {
            if (err) {
                console.log(err);
                toast.success("Error Occured");
            }
        }
    }

    function createOrder() {
        getUserCart();
        const body = {
            "userId": userId,
            "paymentMethod": 'cash',
            "orderItems": [
                {
                    "productId": productId,
                    "quantity": productQuantity
                }
            ]
        }

        const headers = {
            'Authorization': `Bearer ${token}`
        };
        const url = `http://localhost:8080/api/orders/create`;
        axios.post(url, body, { headers })
            .then((res) => {
                toast.success("Payment completed successfully");
                clearCart();
                setTimeout(() => {
                    nav('/allOrders');
                }, 1500)
            }).catch((err) => {
                console.log(err);
                toast.error("Error occured");
            })
    }

    return <>
        <div className="container">
            <h2 className='mt-3  text-center'>Payment</h2>
            <div className="row justify-content-center ">
                <div className="col-md-8">
                    <div className="mb-3">
                        <label htmlFor="exampleFormControlInput1" className="form-label">City</label>
                        <input type="text" className="form-control" id="city" placeholder="Cairo.." />
                    </div>
                </div>
                <div className="col-md-8">
                    <div className="mb-3">
                        <label htmlFor="exampleFormControlInput1" className="form-label">Phone</label>
                        <input type="text" className="form-control" id="phone" placeholder="0123.." />
                    </div>
                </div>
                <div className="col-md-8">
                    <div className="mb-3">
                        <label htmlFor="exampleFormControlTextarea1" className="form-label">Details</label>
                        <textarea className="form-control" id="details" rows="3" placeholder='address..'></textarea>
                    </div>
                </div>
            </div>
            <div className='d-flex justify-content-center text-center gap-2'>
                <div className="col-md-3">
                    <button className='btn btn-primary w-100' onClick={createOrder}>Confirm Cash Payment <i className="fa-solid fa-coins ps-1"></i></button>
                </div>
            </div>
        </div>
    </>
}
