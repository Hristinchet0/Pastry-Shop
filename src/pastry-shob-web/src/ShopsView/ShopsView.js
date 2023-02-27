import React, {useEffect, useRef, useState} from 'react';
import {useLocalState} from "../util/useLocalStorage";
import ajax from "../Services/FetchService";
import {Dropdown, Button, ButtonGroup, Col, Container, DropdownButton, Form, Row, Badge} from "react-bootstrap";
import StatusBadge from "../components/StatusBadge/StatusBadge";
import {useNavigate, useParams} from "react-router-dom";
import {useUser} from "../UserProvider/UserProvider";

const ShopsView = () => {
    let navigate = useNavigate();
    const user = useUser();
    const {shopId} = useParams()
    // const shopId = window.location.href.split("/shops/")[1];
    const [shop, setShop] = useState({
        town: "",
        address: "",
        number: null,
        status: ""
    });

    const [shopsEnums, setShopsEnums] = useState([]);
    const [shopsStatuses, setShopsStatuses] = useState([]);

    const [comment, setComment] = useState({
        text: "",
        shopId: shopId !== null ? parseInt(shopId) : null,
        user: user.jwt,
    });

    const prevShopValue = useRef(shop);

    function submitComment () {
        ajax('/api/comments', 'POST', user.jwt, comment).then(comment => {
        })
    }

    useEffect(() => {
        ajax(`/api/comments?shopId=${shopId}`, 'GET', user.jwt, null)
            .then(comments => console.log(comments));
    }, [])

    function updateComment (value) {
        const commentCopy = {...comment}
        commentCopy.text = value;
        setComment(commentCopy);
    }

    function updateShop(prop, value) {
        const newShop = {...shop}
        newShop[prop] = value;
        setShop(newShop);
    }

    function saveShop() {
        if (shop.status === shopsStatuses[0].status) {
            updateShop("status", shopsStatuses[1].status)
        } else {
            persist();
        }
    }

    function persist() {
        ajax(`/api/shops/${shopId}`, "PUT", user.jwt, shop)
            .then(shopData => {
                    setShop(shopData);
                }
            );
    }

    useEffect(() => {
        if (prevShopValue.current.status !== shop.status) {
            persist();
        }
        prevShopValue.current = shop;
    }, [shop])

    useEffect(() => {
        ajax(`/api/shops/${shopId}`, "GET", user.jwt)
            .then(shopResponse => {
                let shopData = shopResponse.shops;
                if (shopData.town === null) shopData.town = ""
                if (shopData.address === null) shopData.address = ""
                setShop(shopData);
                setShopsEnums(shopResponse.shopsEnums);
                setShopsStatuses(shopResponse.statusEnums);
            });
    }, []);

    return (
        <Container className="mt-4">
            <Row className="d-flex justify-content-center align-items-end">
                <Col>
                    {shop.number ? <h4>Shop: {shop.number}</h4> : <></>

                    }
                </Col>
                <Col>
                    <StatusBadge text={shop.status} />
                </Col>
            </Row>
            {shop ? (
                <>
                    <Form.Group as={Row} className="mb-3" controlId="shopNumber">
                        <Form.Label column sm="2" className="">
                            Shop Number:
                        </Form.Label>
                        <Col sm="10" md="8" lg="6">
                            <DropdownButton
                                as={ButtonGroup}
                                variant={'info'}
                                title={
                                    shop.number
                                        ? `Shop ${shop.number}`
                                        : "Select an Shop"
                                }
                                onSelect={(selectedElement) => {
                                    updateShop("number", selectedElement)
                                }}
                            >
                                {shopsEnums.map((shopsEnum) => (
                                    <Dropdown.Item
                                        key={shopsEnum.shopNumber}
                                        eventKey={shopsEnum.shopNumber}>
                                        {shopsEnum.shopNumber}
                                    </Dropdown.Item>))}
                            </DropdownButton>
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} className="mb-3" controlId="town">
                        <Form.Label column sm="2" className="">
                            Town:
                        </Form.Label>
                        <Col sm="10">
                            <Form.Control
                                onChange={(e) => updateShop("town", e.target.value)}
                                value={shop.town}
                                type="text"
                                placeholder="Town"
                            />
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row} className="mb-3" controlId="address">
                        <Form.Label column sm="2" className="">
                            Address:
                        </Form.Label>
                        <Col sm="10">
                            <Form.Control
                                onChange={(e) => updateShop("address", e.target.value)}
                                value={shop.address}
                                type="text"
                                placeholder="address"
                            />
                        </Col>
                    </Form.Group>
                    <div className="buttons">
                        <Button
                            style={{marginRight: '10px'}}
                            onClick={() => saveShop()}>Submit Shop</Button>
                        <Button
                            id="submit"
                            type="button"
                            onClick={() => {
                                window.location.href = "/shops";
                            }}
                        > Shops
                        </Button>
                    </div>
                    <div className="comments">
                        <textarea
                        onChange={(e) =>  updateComment(e.target.value)}
                        >

                        </textarea>
                        <Button
                        onClick={() => submitComment()}
                        >Post Comment
                        </Button>
                    </div>
                </>
            ) : (
                <></>
            )}
        </Container>
    );
};

export default ShopsView;