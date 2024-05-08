import React, { useContext, useEffect, useState } from 'react'
import Lottie from 'lottie-react';
import img from './Animation - 1709313300851.json'
import { Link } from 'react-router-dom';
import EmptyAnimation from '../EmtyAnimation/EmptyAnimation';
import axios from 'axios';
import toast from 'react-hot-toast';

export default function Cart() {
  const token = localStorage.getItem('token');
  const userId = localStorage.getItem('userId');
  const [allProducts, setAllProducts] = useState(null);


  async function getUserCart() {
    const headers = {
      'Authorization': `Bearer ${token}`
    };
    const url = `http://localhost:8080/cart/getCart/${userId}`
    await axios.get(url, { headers })
      .then((res) => {
        setAllProducts(res.data);
        console.log(res);
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
      setAllProducts([]);
      toast.success("Cart cleared successfully");
    } catch (err) {
      if (err) {
        console.log(err);
        toast.success("Error Occured");
      }
    }
  }

  useEffect(() => {
    getUserCart();
  }, [])


  if (!allProducts) {
    return <EmptyAnimation />
  }

  return <>
    <div className="container">
      {allProducts.length ? <div>
        <h1 className='mt-2'>Shopping Cart :</h1>
        <div className='d-flex justify-content-between container'>
          <button className='btn btn-danger d-block ms-auto' onClick={clearCart}>Clear Cart</button>
        </div>
        <hr />
        <div className="container">
          {allProducts.map((product, idx) => <div className='row' key={idx}>
            <div className="col-md-1 mb-1">
              <img src={`${product.thumbNail}`} alt={product.name} className='w-100' />
            </div>
            <div className="col-md-9">
              <h5>{product.name}</h5>
              <h5>price : {product.price}</h5>
            </div>
            <hr className='mt-2' />
          </div>)}
          <div>
            <Link to="/payment">
              <button className='btn btn-success d-block m-auto'>Confirm Payment</button>
            </Link>
          </div>
        </div>
      </div> : <div className='d-flex flex-column align-items-center'>
        <div className="col-md-5 col-12">
          <div className='text-center'>
            <h2 className='pt-3'>Your Cart is empty !</h2>
            <Lottie animationData={img}></Lottie>
          </div>
        </div>
      </div>}


    </div>
  </>
}
