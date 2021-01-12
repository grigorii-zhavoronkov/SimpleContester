import {Table} from "react-bootstrap";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import API from "../api"

function AttemptsView() {

    const [attempts, setAttempts] = useState([]);

    useEffect(() => {
        API.get("/attempt/getAttempts")
            .then(function (response) {
                setAttempts(response.data);
                console.log(response.data);
            })
    }, [])

    function getStatus(attempt){
        if (attempt.status) {
            switch (attempt.status) {
                case "WRONG_ANSWER":
                case "TIME_LIMIT_EXCEED":
                case "MEMORY_LIMIT_EXCEED":
                    return `${attempt.status}#${attempt.lastTestNumber}`
                default:
                    return `${attempt.status}`
            }
        }
    }

    return (
        <div>
            <Table>
                <thead>
                <tr>
                    <th>id</th>
                    <th>name</th>
                    <th>task</th>
                    <th>status</th>
                </tr>
                </thead>
                <tbody>
                {attempts.map(attempt => {
                    return (
                        <tr key={attempt.id}>
                            <td>{attempt.id}</td>
                            <td>{attempt.senderName}</td>
                            <td><Link to={`/task/${attempt.taskId}`}>{attempt.taskTitle}</Link></td>
                            <td>{getStatus(attempt)}</td>
                        </tr>
                    )
                })}
                </tbody>
            </Table>
        </div>
    )
}

export default AttemptsView;
