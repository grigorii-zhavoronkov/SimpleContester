import {useEffect, useState} from "react";

import API from "../api";
import TaskTable from "../TaskTable/TaskTable";
import {toast} from "react-toastify";

function Home() {

    const [tasks, setTasks] = useState([]);

    useEffect(() => {
        API.get("task/getAll")
            .then(function(response) {
                setTasks(response.data);
                console.log(response.data);
            })
            .catch(function(err) {
                toast.error(err.toString());
                console.log(err);
        })
    }, []);

    return (
        <div>
            <TaskTable tasks={tasks} />
        </div>
    )
}

export default Home;
