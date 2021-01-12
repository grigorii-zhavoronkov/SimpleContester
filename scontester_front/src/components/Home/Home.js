import {useEffect, useState} from "react";

import API from "../api";
import TaskTable from "../TaskTable/TaskTable";

function Home() {

    const [tasks, setTasks] = useState([]);

    useEffect(() => {
        /*API.get("task/getAll")
            .then(function(response) {
                setTasks(response.data);
            })*/
        setTasks([
            {
                "id": 1,
                "title": "a+b"
            },
            {
                "id": 2,
                "title": "a+b333"
            },
            {
                "id": 3,
                "title": "a+b334"
            }
        ])
    }, []);

    return (
        <div>
            <TaskTable tasks={tasks} />
        </div>
    )
}

export default Home;
