import React, { useContext, useEffect, useState } from 'react'
import Lottie from 'lottie-react';
import img from './Animation - 1709313300851.json'
import axios from 'axios';
import toast from 'react-hot-toast';

export default function Wishlist() {
  const token = localStorage.getItem('token');
  const userId = localStorage.getItem('userId');
  const [allWishProducts, setAllWishProducts] = useState([]);
  const [idProducts, setIdProducts] = useState([]);

  const extractPropertyValues = () => {
    if (allWishProducts.length > 0) {
      const newPropertyValues = allWishProducts.map(product => product.id);
      setIdProducts(newPropertyValues);
    }
  };

  function removeFromWish(productId) {
    const headers = {
      'Authorization': `Bearer ${token}`
    };
    const url = `http://localhost:8080/api/v1/wishlist/delete-from-wishlist/${productId}/${userId}`;
    axios.delete(url, { headers })
      .then((res) => {
        toast.success("Item removed successfully", { duration: 2000, position: 'top-center' });
        // setAllWishProducts(res.data);
        getUserWish();
      }).catch((err) => {
        toast.error("Error Occured", { duration: 2000, position: 'top-center' });
      })
  }

  function addToWish(productId) {
    const headers = {
      'Authorization': `Bearer ${token}`
    };
    const url = `http://localhost:8080/api/v1/wishlist/add-to-wishlist/${productId}/${userId}`;
    axios.post(url, null, { headers })
      .then((res) => {
        if (idProducts.includes(productId)) {
          removeFromWish(productId);
        }
        else {
          toast.success('Added to Wishlist', { duration: 2000, position: 'top-center' })
          getUserWish();
        }
        getUserWish();
      }).catch((err) => {
        console.error(err);
        toast.error('Error occurred', { duration: 2000, position: 'top-center' });
      });
  }

  function getUserWish() {
    const headers = {
      'Authorization': `Bearer ${token}`
    };
    const url = `http://localhost:8080/api/v1/wishlist/get-products-in-wshlist/${userId}`;
    axios.get(url, { headers })
      .then((res) => {
        // console.log(res);
        setAllWishProducts(res.data);
        extractPropertyValues();
        if (allWishProducts.length > 0) {
          extractPropertyValues();
        }
      }).catch((err) => {
        console.log('failed', err);
      })
  }

  function addProductTCart(productId) {
    const headers = {
      'Authorization': `Bearer ${token}`
    };
    const url = `http://localhost:8080/cart/addToCart/${productId}/${userId}`;
    axios.post(url, null, { headers })
      .then((res) => {
        toast.success("Added to cart", { duration: 1500, position: "top-center" });
        console.log(res);
      })
      .catch((err) => {
        toast.error("Error Occured", { duration: 1500, position: "top-center" });
        console.log(err);
      });
  }

  useEffect(() => {
    getUserWish();
  }, []);

  return <>
    <div className="container">
      {allWishProducts.length ? <div>
        <h1 className='mt-2'>Wishlist</h1>
        <div className='d-flex justify-content-between container'>
        </div>
        <hr />
        <div className="container">
          {allWishProducts.map((product, idx) => <div className='row' key={idx}>
            <div className="col-md-1 mb-1">
              <img src={product.thumbNail} alt={product.name} className='w-100' />
            </div>
            <div className="col-md-9">
              <h5>{product.name}</h5>
              <h5>price : {product.price} $</h5>
              <button className='btn btn-outline-danger' onClick={() => removeFromWish(product.id)}>Remove  <i className="fa-regular fa-trash-can"></i></button>
            </div>
            <div className="col-md-2">
              <button className='btn btn-success w-100' onClick={() => addProductTCart(product.id)}>Add to Cart<i className="fa-solid fa-cart-plus ps-2"></i></button>
            </div>
            <hr className='mt-2' />
          </div>)}
        </div>
      </div>
        : <div className='d-flex flex-column align-items-center'>
          <div className="col-md-5 col-12">
            <div className='text-center'>
              <h2 className='pt-3'>Empty !</h2>
              <Lottie animationData={img}></Lottie>
            </div>
          </div>
        </div>}
    </div>
  </>
}
