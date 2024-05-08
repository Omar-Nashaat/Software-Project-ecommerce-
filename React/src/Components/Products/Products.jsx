import React, { useContext, useState } from "react";
import { counterContext } from "../../Context/product";
import HomeSlider from "../HomeSlider/HomeSlider";
import img2 from "../Products/images/slider-image-2.jpeg";
import img3 from "../Products/images/slider-image-3.jpeg";
import CategorySlider from "../CategorySlider/CategroySlider";
import style from "./Products.module.css";
import axios from "axios";
import Loader from "../Loader/Loader";
import { useQuery } from "react-query";
import img from './Animation - 1709313300851.json'
import { Link } from "react-router-dom";
import Lottie from "lottie-react";
import toast from "react-hot-toast";

export default function Products() {
  const { addProductTCart } = useContext(counterContext);
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

  function getAllProducts() {
    const headers = {
      'Authorization': `Bearer ${token}`
    };
    return axios.get("//localhost:8080/api/v1/admin/get-all-products", { headers });
  }

  function removeFromWish(productId) {
    const headers = {
      'Authorization': `Bearer ${token}`
    };
    const url = `http://localhost:8080/api/v1/wishlist/delete-from-wishlist/${productId}/${userId}`;
    axios.delete(url, { headers })
      .then((res) => {
        toast.success("Item removed successfully", { duration: 2000, position: 'top-center' });
        setAllWishProducts(res.data);
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
        setAllWishProducts(res.data);
        extractPropertyValues();
        if (allWishProducts.length > 0) {
          extractPropertyValues();
        }
      }).catch((err) => {
        console.log('failed', err);
      })
  }

  const { data, isLoading } = useQuery("getProducts", getAllProducts);

  if (isLoading) {
    return <Loader />;
  }

  return (
    <>
      <div className="container">
        <div className="row">
          {data?.data.map((product, idx) => (
            <div
              key={idx}
              className={style.product + " col-md-2 overflow-hidden mb-3"}
            >
              <Link to={`/productDetails/${product.id}`}>
                <div className="cursor-pointer">
                  <figure className="position-relative">
                    <img
                      src={img2}
                      className="w-100"
                      alt={product.name}
                      style={{ height: "200px" }}
                    />
                  </figure>
                  <figcaption className="ps-2 pe-2">
                    <h4 className="text-center">
                      {product.name.split(" ").slice(0, 2).join(" ").toUpperCase()}
                    </h4>

                    <div className="d-flex justify-content-between">
                      <p>{product.price} LE</p>
                      <div className="d-flex">
                        <p>{product.rating}</p>
                        <i
                          className="fa-solid fa-star pt-1 ps-1"
                          style={{ color: "#FFD43B" }}
                        />
                      </div>
                    </div>
                  </figcaption>
                </div>
              </Link>
              <button
                className="btn btn-success w-100 mb-1"
                onClick={() => addProductTCart(product.id)}
              >
                Add to Cart<i className="fa-solid fa-cart-plus ps-2"></i>
              </button>
              {idProducts.includes(product.id) ? <button className="btn btn-danger w-100" onClick={() => removeFromWish(product.id)}>Already In Wishlist</button>
                : <button className="btn btn-outline-danger w-100" onClick={() => addToWish(product.id)}>Add To Wishlist <i className="fa-regular fa-heart ps-2"></i></button>
              }
            </div>
          ))}
        </div>
      </div>
    </>
  );
}
