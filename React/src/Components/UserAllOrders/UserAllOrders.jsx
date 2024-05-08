import axios from "axios";
import React, { useEffect, useState } from "react";
import Lottie from "lottie-react";
import img from './Animation - 1709313300851.json'


export default function UserAllOrders() {
  const [UserAllOrders, setUserAllOrders] = useState([]);
  const token = localStorage.getItem('token');

  useEffect(() => {
    (function getUserOrders() {
      const headers = {
        'Authorization': `Bearer ${token}`
      };
      axios.get(`http://localhost:8080/api/orders/all`, { headers })
        .then((res) => {
          console.log(res);
          setUserAllOrders(res.data);
        })
        .catch((err) => {
          console.log("failed: ", err);
        });
    })();
  }, []);


  if (UserAllOrders.length === 0) {
    return (
      <div className="d-flex flex-column align-items-center mt-4">
        <div className="col-md-4 col-12">
          <div className="text-center">
            <Lottie animationData={img}></Lottie>
            <h2>No orders yet!</h2>
          </div>
        </div>
      </div>
    );
  }

  return (
    <>
      <div className="container">
        <div>
          <h1 className="mt-2 text-center">All Your Orders</h1>
          <div className="d-flex justify-content-between container"></div>
          <hr />
          <div className="container text-center">
            <div className="row">
              {UserAllOrders.map((order, idx) => (
                <div key={idx} className="col-md-4">
                  <div className="bg-body-secondary order m-2 p-2">
                    <h5>
                      <i class="fa-solid fa-hand-holding-dollar pe-2"></i>Total
                      Price : {order.totalPrice}
                    </h5>
                    <h5>
                      <i class="fa-solid fa-list-ol pe-2"></i>Number of items :{" "}
                      { order.orderItems.length}
                    </h5>
                    <h5>
                      <i class="fa-solid fa-credit-card pe-2"></i>Payment Method
                      : {order.paymentMethod}
                    </h5>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
