<script>

    import CheckAdminLogin from "./CheckAdminLogin.svelte";
    import {link} from 'svelte-spa-router';
    import {Table, Spinner, Button, Modal, ModalHeader, ModalBody, ModalFooter, FormGroup, Label, Input, CustomInput} from "sveltestrap";
    import {onMount} from 'svelte';
    import axios from 'axios';
    import Logout from './Logout.svelte';
    import CheckSenderLogin from "./CheckSenderLogin.svelte";

    onMount(() => loadTasks())

    export let isAdmin = false;

    let tasks = [];
    let newTask = {
        id: "",
        title: "",
        description: "",
        inputType: "STDIN",
        tests: [{input: "", output: ""}]
    }
    let createTaskModalOpen = false;
    const toggle = () => (createTaskModalOpen = !createTaskModalOpen)

    function loadTasks() {
        axios.get("/task/getAll")
            .then(r => {
                if (r.status === 200) {
                    tasks = r.data
                }
            });
    }

    function createTask() {
        const password = localStorage.getItem("password");
        if (password) {
            axios.post("admin/api/createTask", newTask,
                {
                    headers: {
                        "Authorization": password
                    }
                }).then(() => {
                    newTask = {id: "", title: "", description: "", inputType: "STDIN", tests: [{input: "", output: ""}]}
                    loadTasks();
                }).finally(() => {
                    toggle();
                })
        }
    }

    function deleteTask(taskId) {
        const password = localStorage.getItem("password");
        if (password)
        axios.post("admin/api/deleteTask", {id: taskId}, {
            headers: {
                "Authorization": password
            }
        }).then(() => loadTasks())
    }

</script>

<div class="container">
    {#if isAdmin}
        <CheckAdminLogin />
        <Button color="primary" on:click={toggle}>Добавить задание</Button>
        <br />
        <br />
    {:else}
        <Logout />
        <CheckSenderLogin />
    {/if}

    {#if tasks.length !== 0}
        <Table bordered>
            <thead>
            <tr>
                <th>ID</th>
                <th>Название задачи</th>
                {#if isAdmin}
                    <th>Удалить</th>
                {/if}
            </tr>
            </thead>
            {#each tasks as task (task.id)}
                <tr>
                    <td><a use:link={`/task/${task.id}`}>{task.id}</a></td>
                    <td>{task.title}</td>
                    {#if isAdmin}
                        <td><Button color="danger" on:click={deleteTask(task.id)}>X</Button></td>
                    {/if}
                </tr>
            {/each}
        </Table>
    {:else}
        <Spinner color="primary" />
    {/if}
</div>
<Modal isOpen={createTaskModalOpen} {toggle}>
    <ModalHeader {toggle}>Добавить задание</ModalHeader>
    <ModalBody>
        <FormGroup>
            <Label>ID задания</Label>
            <Input bind:value={newTask.id} type="number" placeholder="Обязательно для заполнения"/>
        </FormGroup>
        <FormGroup>
            <Label>Заголовок</Label>
            <Input bind:value={newTask.title} type="text" maxlength="255"/>
        </FormGroup>
        <FormGroup>
            <Label>Описание</Label>
            <Input bind:value={newTask.description} type="textarea" maxlength="2000"/>
        </FormGroup>
        <FormGroup check>
            <Label check>Поток ввода/вывода</Label>
            <CustomInput id="stdin"
                         on:change={() => newTask = {...newTask, inputType: "STDIN"}}
                         type="radio"
                         label="Стандартный поток"
                         name="inputType"
                         defaultChecked/>
            <CustomInput id="file"
                         on:change={() => newTask = {...newTask, inputType: "FILE"}}
                         type="radio"
                         name="inputType"
                         label="Ввод/вывод через файл"/>
        </FormGroup>
        <hr />
        {#each newTask.tests as test, id}
            <FormGroup>
                <Label>Ввод для теста {id+1}</Label>
                <Input bind:value={newTask.tests[id].input} type="textarea" maxlength="255" />
            </FormGroup>
            <FormGroup>
                <Label>Вывод для теста {id+1}</Label>
                <Input bind:value={newTask.tests[id].output} type="textarea" maxlength="255" />
            </FormGroup>
            <Button color="danger"
                    on:click={() => newTask.tests = newTask.tests.filter((el, idx) => id !== idx)}>
                Удалить тест {id+1}</Button>
            <hr />
        {/each}
        <Button color="primary"
                on:click={() => newTask.tests = [...newTask.tests, {input: "", output: ""}]}>
            Добавить тест</Button>
    </ModalBody>
    <ModalFooter>
        <Button color="primary" on:click={createTask}>Добавить задание</Button>
        <Button color="danger" on:click={toggle}>Отмена</Button>
    </ModalFooter>
</Modal>


<style>
    table a {
        cursor: pointer;
        color: blue;
    }

    table a:hover {
        text-decoration: underline;
    }
</style>