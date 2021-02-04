import {Button, Col, Form, Modal, Table} from "react-bootstrap";
import {useEffect, useState} from "react";
import API from "../../api";
import {toast} from "react-toastify";

function Senders() {

    const [senders, setSenders] = useState([]);
    const [newSenderName, setNewSenderName] = useState("");
    const [showNewSenderModal, setShowNewSenderModal] = useState(false);
    const handleShowNewSenderModal = () => setShowNewSenderModal(true);
    const handleCloseNewSenderModal = () => setShowNewSenderModal(false);

    const password = localStorage.getItem("Authorization");

    function createSender() {
        handleCloseNewSenderModal();
        API.post("admin/api/createSender", {name: newSenderName}, {headers: {
                "Authorization": password
            }})
            .then(function (response) {
                if (response.status === 200) {
                    toast.success(response.data.toString())
                }
            }).catch(function (err) {
                toast.error(err.toString())
        });
        loadSenders();
    }

    function loadSenders() {
        API.get("admin/api/getSenders", {
            headers: {
                "Authorization": password
            }
        }).then(function (response) {
            if (response.status === 200) {
                setSenders(response.data)
            }
        }).catch(function (err) {
            toast.error(err.toString)
        })
    }

    useEffect(() => {
        loadSenders();
    }, [])

    return (
        <div>
            <Modal show={showNewSenderModal} onHide={handleCloseNewSenderModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Создать отправителя</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="newSenderName">
                            <Form.Label>Имя</Form.Label>
                            <Form.Control type="text" value={newSenderName} onChange={(event => setNewSenderName(event.target.value))}/>
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseNewSenderModal}>
                        Закрыть
                    </Button>
                    <Button variant="primary" onClick={createSender}>
                        Создать
                    </Button>
                </Modal.Footer>
            </Modal>
            <Table bordered>
                <thead>
                <tr>
                    <th>UID</th>
                    <th>name</th>
                </tr>
                </thead>
                <tbody>
                {senders.map(sender => {
                    return (
                        <tr key={sender.uid}>
                            <td>{sender.uid}</td>
                            <td>{sender.name}</td>
                        </tr>
                    )
                })}
                </tbody>
            </Table>
            <Button onClick={handleShowNewSenderModal}>Создать отправителя</Button>
        </div>
    )
}

export default Senders;