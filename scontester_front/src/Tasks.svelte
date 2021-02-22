<script>

    import CheckAdminLogin from "./CheckAdminLogin.svelte";

    export let isAdmin = false;

    import {link} from 'svelte-spa-router';
    import {Table, Spinner, Icon, Button} from "sveltestrap";
    import {onMount} from 'svelte';
    import axios from 'axios';
    import Logout from './Logout.svelte';
    import CheckSenderLogin from "./CheckSenderLogin.svelte";

    onMount(() => loadTasks())

    let tasks = []

    function loadTasks() {
        axios.get("/task/getAll")
            .then(r => {
                if (r.status === 200) {
                    tasks = r.data
                }
            });
    }

    function deleteTask(taskId) {

    }

</script>

<main>
    {#if isAdmin}
        <CheckAdminLogin />
        <Button color="primary">Добавить задание</Button>
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
</main>

<style>
    table a {
        cursor: pointer;
        color: blue;
    }

    table a:hover {
        text-decoration: underline;
    }
</style>