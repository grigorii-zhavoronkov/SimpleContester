import {Table} from "react-bootstrap";
import {useEffect, useState} from "react";

function AttemptsView() {

    const [attempts, setAttempts] = useState([]);

    useEffect(() => {
        // api request
        setAttempts([
            {
                "id": 2,
                "senderName": "Ivanov Ivan Ivanovich",
                "taskId": 1,
                "taskTitle": "a+b",
                "status": "OK",
                "lastTestNumber": 0
            },
            {
                "id": 1,
                "senderName": "Ivanov Ivan Ivanovich",
                "taskId": 1,
                "taskTitle": "a+b",
                "status": "WA",
                "lastTestNumber": 3
            },
        ])
    }, [])

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
                            <td>{attempt.taskTitle}</td>
                            <td>{attempt.status}</td>
                        </tr>
                    )
                })}
                </tbody>
            </Table>
        </div>
    )
}

export default AttemptsView;
