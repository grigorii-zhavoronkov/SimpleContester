import 'bootstrap/dist/css/bootstrap.min.css';
import {Table} from "react-bootstrap";
import {Link} from "react-router-dom";

function TaskTable (props) {
    return (
        <div>
            <Table bordered hover>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                </tr>
                </thead>
                <tbody>
                {props.tasks.map(task => {
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
        </div>
    )
}

export default TaskTable;
