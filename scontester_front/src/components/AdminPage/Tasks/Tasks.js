import {Button, Form, Modal, Table} from "react-bootstrap";
import {useEffect, useState} from "react";
import API from "../../api";
import {toast} from "react-toastify";
import {Link} from "react-router-dom";

function Tasks() {

    const password = localStorage.getItem("Authorization");

    const [tasks, setTasks] = useState([]);
    const [showNewTaskModal, setShowNewTaskModal] = useState(false);
    const handleShowNewTaskModal = () => setShowNewTaskModal(true);
    const handleCloseNewTaskModal = () => setShowNewTaskModal(false);
    const [newTask, setNewTask] = useState({
        title: '',
        description: '',
        inputType: 'STDIN',
        tests: [
            {input: '', output: ''}
        ]
    })

    useEffect(() => {
        loadTasks();
    }, []);

    function loadTasks() {
        API.get("task/getAll")
            .then(function(response) {
                setTasks(response.data);
            })
            .catch(function(err) {
                toast.error(err.toString());
            })
    }

    function createTask() {
        handleCloseNewTaskModal();
        API.post("admin/api/createTask", newTask,
        {
            headers: {
                "Authorization": password
            }
        }).then(function (response) {
            if (response.status === 200) {
                toast.success("Задание создано")
            }
        }).catch(function (err) {
            toast.error(err.toString())
        });
        loadTasks();
    }

    function updateNewTask(event) {
        setNewTask({...newTask, [event.target.id]: event.target.value})
        console.log(newTask);
    }

    function updateTests(event) {
        const trueId = Math.floor(event.target.id/2);
        let task = {...newTask}
        const isInput = (event.target.id % 2 === 0);
        if (isInput) {
            task.tests[trueId].input = event.target.value;
        } else {
            task.tests[trueId].output = event.target.value;
        }
        setNewTask(task);
    }

    function deleteTests(id) {
        let task = {...newTask};
        task.tests = newTask.tests.filter((el, idx) => id !== idx);
        setNewTask(task);
    }

    function createTest(e) {
        let task = {...newTask};
        task.tests = [...newTask.tests, {input: '', output: ''}];
        setNewTask(task);
    }

    return (
        <div>
            <Modal show={showNewTaskModal} onHide={handleCloseNewTaskModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Создать задачу</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="title">
                            <Form.Label>Название</Form.Label>
                            <Form.Control type="text" onChange={updateNewTask} value={newTask.title}/>
                        </Form.Group>
                        <Form.Group controlId="description">
                            <Form.Label>Описание</Form.Label>
                            <Form.Control type="text" as="textarea" rows={5} onChange={updateNewTask} value={newTask.description}/>
                        </Form.Group>
                        <fieldset>
                            <Form.Group controlId="inputType">
                                <Form.Label>Ввод</Form.Label>
                                <Form.Check type="radio" label="Стандартный поток ввода/вывода" name="inputType" defaultChecked onClick={() =>
                                    setNewTask({...newTask, inputType: 'STDIN'})} />
                                <Form.Check type="radio" label="Ввод/вывод из файла" name="inputType" onClick={() =>
                                    setNewTask({...newTask, inputType: 'FILE'})
                                } />
                            </Form.Group>
                        </fieldset>
                        {newTask.tests.map((test, id) => {
                            return (
                                <div key={id}>
                                    <Form.Group controlId={id*2}>
                                        <Form.Label>Ввод для теста {id+1}</Form.Label>
                                        <Form.Control as="textarea" rows={3} type="text" onChange={updateTests} value={newTask.tests[id].input} />
                                    </Form.Group>
                                    <Form.Group controlId={id*2+1}>
                                        <Form.Label>Вывод для теста {id+1}</Form.Label>
                                        <Form.Control as="textarea" rows={3} type="text" onChange={updateTests} value={newTask.tests[id].output} />
                                    </Form.Group>
                                    <Button variant="danger" onClick={() => deleteTests(id)}>Удалить тест {id+1}</Button>
                                </div>
                            )
                        })}
                    </Form>
                    <br />
                    <Button variant="primary" onClick={createTest}>Добавить тест</Button>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseNewTaskModal}>
                        Закрыть
                    </Button>
                    <Button variant="primary" onClick={createTask}>
                        Создать
                    </Button>
                </Modal.Footer>
            </Modal>
            <Table bordered hover>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                </tr>
                </thead>
                <tbody>
                {tasks.map(task => {
                    return (
                        <tr key={task.id}>
                            <td>
                                <Link to={`/task/${task.id}`}>
                                    {task.id}
                                </Link>
                            </td>
                            <td>{task.title}</td>
                        </tr>
                    )
                })}
                </tbody>
            </Table>
            <Button onClick={handleShowNewTaskModal}>Создать задачу</Button>
        </div>
    )
}

export default Tasks;