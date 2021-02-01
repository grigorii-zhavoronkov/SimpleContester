import {useEffect, useState} from "react";
import {Table, Form, Button} from "react-bootstrap";
import { useHistory, useParams  } from "react-router-dom";
import API from "../api";
import {toast} from "react-toastify";

function TaskView() {

    const history = useHistory();

    let { id } = useParams();
    const [task, setTask] = useState({});
    const [attempt, setAttempt] = useState({
        "taskId": id,
        "uid": "",
        "programmingLanguage": "FREE_PASCAL",
        "code": ""
    })

    function onFormChange(event) {
        setAttempt({...attempt, [event.target.id]: event.target.value});
    }

    function submit(event) {
        attempt.code = attempt.code.replaceAll("\t", "    ");
        API.post("attempt/sendAttempt", attempt)
            .then(function (response) {
                if (response.status !== 200) {
                    toast.error(response.data);
                } else {
                    history.push("/attempts")
                    event.preventDefault();
                }
            }).catch(function(err) {
                toast.error(err.toString());
                console.log(err);
            });
    }

    useEffect(() => {
        API.get("task/get", {
            params: {id}
        }).then(function(response) {
            if (response.status === 200) {
                setTask(response.data)
            }
        }).catch(function(err) {
            toast.error(err.toString());
            console.log(err);
        })
    }, []);

    return (
        <div>
            <h1>{task.id}. {task.title}</h1>
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
                {task.tests && task.tests.map(test => {
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
                <Form.Group controlId="uid">
                    <Form.Label>UID</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="AAA11"
                        maxLength="5"
                        value={attempt.uid}
                        onChange={onFormChange}/>
                </Form.Group>
                <Form.Group controlId="programmingLanguage">
                    <Form.Label>Выберите язык</Form.Label>
                    <Form.Control as="select" value={attempt.language} onChange={onFormChange}>
                        <option value="FREE_PASCAL">Pascal (FPC)</option>
                        <option value="JAVA">Java 11</option>
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
