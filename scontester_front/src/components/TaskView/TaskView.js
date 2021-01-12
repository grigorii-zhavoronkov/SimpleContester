import {useEffect, useState} from "react";
import {Table, Form, Button} from "react-bootstrap";
import { useHistory, useParams  } from "react-router-dom";

function TaskView() {

    const history = useHistory();

    let { id } = useParams();
    const [task, setTask] = useState({});
    const [attempt, setAttempt] = useState({
        "taskId": id,
        "UID": "",
        "language": "PASCAL",
        "code": ""
    })

    function onFormChange(event) {
        setAttempt({...attempt, [event.target.id]: event.target.value});
    }

    function submit(event) {
        console.log(attempt);
        //api
        history.push("/attempts")
        event.preventDefault();
    }

    useEffect(() => {
        setTask({
            "id": 1,
            "title": "a+b",
            "description": "Sum of two integers",
            "inputType": "STDIN",
            "timeLimit": 1,
            "memoryLimit": 64,
            "tests": [
                {
                    "id": 1,
                    "task": null,
                    "input": "2\n2",
                    "output": "4"
                },
                {
                    "id": 2,
                    "task": null,
                    "input": "3\n3",
                    "output": "6"
                }
            ]
        });
    });

    return (
        <div>
            <h1>{task.id}. {task.title}</h1>
            <p>Ограничение по времени: {task.timeLimit} сек.</p>
            <p>Ограничение по памяти: {task.memoryLimit} МБ</p>
            <p>
                {task.description}
            </p>
            <Table bordered>
                <thead>
                    <tr>
                        <th>{task.inputType === "STDIN" ? "STDIN" : "input.txt"}</th>
                        <th>{task.inputType === "STDIN" ? "STDOUT" : "output.txt"}</th>
                    </tr>
                </thead>
                <tbody>
                {task.tests.map(test => {
                    return (
                        <tr key={test.id}>
                            <td><pre>{test.input}</pre></td>
                            <td><pre>{test.output}</pre></td>
                        </tr>
                    )
                })}
                </tbody>
            </Table>
            <Form>
                <Form.Group controlId="UID">
                    <Form.Label>UID</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="AAA11"
                        maxLength="5"
                        value={attempt.UID}
                        onChange={onFormChange}/>
                </Form.Group>
                <Form.Group controlId="language">
                    <Form.Label>Выберите язык</Form.Label>
                    <Form.Control as="select" value={attempt.language} onChange={onFormChange}>
                        <option value="PASCAL">Pascal (FPC)</option>
                        <option value="JAVA">Java 8</option>
                        <option value="PYTHON2">Python 2</option>
                        <option value="PYTHON3">Python 3</option>
                        <option value="C">C</option>
                        <option value="CPP">C++</option>
                    </Form.Control>
                </Form.Group>
                <Form.Group controlId="code">
                    <Form.Label>Исходный код</Form.Label>
                    <Form.Control as="textarea" rows={3} value={attempt.code} onChange={onFormChange} />
                </Form.Group>
                <Button onClick={submit}>Отправить</Button>
            </Form>
        </div>
    )
}

export default TaskView;