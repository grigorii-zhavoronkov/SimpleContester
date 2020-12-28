function TaskTable (props) {
    return (
        <div>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                </tr>
                {props.tasks.map(task => {
                    return (
                        <tr>
                            <td>{task.id}</td>
                            <td>{task.title}</td>
                        </tr>
                    )
                })}
            </table>
        </div>
    )
}

export default TaskTable;
