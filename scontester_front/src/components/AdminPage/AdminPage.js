import {Button, Col, Container, Form, Modal, Nav, Tab, Table, Tabs} from "react-bootstrap";
import API from "../api";
import {useEffect, useState} from "react";
import {useHistory} from "react-router";
import {toast} from "react-toastify";
import Senders from "./Senders/Senders";
import Tasks from "./Tasks/Tasks";
import Attempts from "./Attempts/Attempts";

function AdminPage() {

    const history = useHistory();

    const [tab, setTab] = useState('senders')

    const password = localStorage.getItem("Authorization");

    useEffect(() => {
        API.get("admin/api/login", {
            headers: {
                "Authorization": password
            }
        }).then(function (response) {
            if (response.status !== 200) {
                history.push("/login")
            }
        }).catch(function (err) {
            toast.error("Авторизация не удалась. Попробуйте снова.")
            history.push("/login")
        })
    }, [])

    return (
        <Container>
            <Tabs activeKey={tab} onSelect={(t) => setTab(t)}>
                <Tab eventKey="senders" title="Ученики"><Senders /></Tab>
                <Tab eventKey="tasks" title="Задачи"><Tasks /></Tab>
                <Tab eventKey="attempts" title="Попытки"><Attempts /></Tab>
            </Tabs>
        </Container>
    )
}

export default AdminPage;