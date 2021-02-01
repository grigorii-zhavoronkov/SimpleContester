import {useEffect, useState} from "react";
import API from "../../api";
import {Link} from "react-router-dom";
import {toast} from "react-toastify";
import {Table} from "react-bootstrap";

function Attempts() {

    const [attempts, setAttempts] = useState([]);

    const password = localStorage.getItem("Authorization");

    useEffect(() => {
        API.get("admin/api/getAttempts", {
            headers: {
                "Authorization": password
            }
        }).then(function (response) {
            if (response.status === 200) {
                setAttempts(response.data)
            }
        }).catch(function (err) {
            toast.error(err.toString)
        })
    }, [])

    return (
        <div>
            <Table bordered>
                <thead>
                <tr>
                    <th>id</th>
                    <th>name</th>
                    <th>task</th>
                    <th>status</th>
                    <th>last test number</th>
                </tr>
                </thead>
                <tbody>
                {attempts && attempts.map(attempt => {
                    return (
                        <tr key={attempt.id}>
                            <td>{attempt.id}</td>
                            <td>{attempt.senderName}</td>
                            <td><Link to={`/task/${attempt.taskId}`}>{attempt.taskTitle}</Link></td>
                            <td>{attempt.status}</td>
                            <td>{attempt.lastTestNumber}</td>
                        </tr>
                    )
                })}
                </tbody>
            </Table>
        </div>
    )
}

export default Attempts;