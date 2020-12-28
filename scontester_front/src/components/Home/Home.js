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
                "title": "a+b",
                "description": "Sum of two integers",
                "testsFile": "/Users/grigoriy/scnotester_test/tasks/task_a+b.csv",
                "inputType": "STDIN",
                "timeLimit": 1,
                "memoryLimit": 64,
                "attempts": []
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
