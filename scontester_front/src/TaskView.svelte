<script>
    import Logout from "./Logout.svelte";

    export let params = {};
    export let isAdmin = false;

    import {Table, Spinner, Form, FormGroup, Label, Input, Button} from "sveltestrap";
    import {onMount} from "svelte";
    import {push} from "svelte-spa-router"
    import axios from 'axios';
    import BackLink from "./BackLink.svelte";

    const id = params.id;
    let task = {};
    let uid = localStorage.getItem("UID");

    let attempt = {
        "taskId": id,
        "uid": uid,
        "programmingLanguage": "FREE_PASCAL",
        "code": ""
    }

    onMount(() => loadTask())

    function loadTask() {
        axios.get("/task/get", {params: {id}})
            .then(r => {
                if (r.status === 200) {
                    task = r.data
                }
            })
            .catch(e => {
                task.id = -1;
                task.err = e.toString();
            })
    }

    function submitAttempt() {
        attempt.code = attempt.code.replaceAll("\t", "    ");
        axios.post("attempt/sendAttempt", attempt)
            .then(function (resp) {
            })
            .catch(function (err) {
                console.log(err);
            })
            .finally(() => push("/attempts"));
    }
</script>

<div class="container">
    <Logout />
    <BackLink />
    {#if task.id === -1}
        <p>Ошибка при загрузке</p>
        <p>{task.err}</p>
    {:else if task.id }
        <h3>{task.id}. {task.title}</h3>
        <p>{task.description}</p>
        <Table bordered>
            <thead>
            <tr>
                <th>{task.inputType === "STDIN" ? "STDIN" : "input.txt"}</th>
                <th>{task.inputType === "STDIN" ? "STDOUT" : "output.txt"}</th>
            </tr>
            </thead>
            <tbody>
            {#each task.tests as test}
                <tr>
                    <td><pre>{test.input}</pre></td>
                    <td><pre>{test.output}</pre></td>
                </tr>
            {/each}
            </tbody>
        </Table>
        {#if !isAdmin}
            <p>Вы будете отправлять задания от <span class="font-bold">{attempt.uid}</span></p>
            <FormGroup>
                <Label for="programmingLanguage">Выберите язык программирования</Label>
                <Input type="select" name="programmingLanguage" bind:value={attempt.programmingLanguage}>
                    <option value="FREE_PASCAL">Pascal (FPC)</option>
                    <option value="JAVA">Java 11</option>
                    <option value="PYTHON2">Python 2</option>
                    <option value="PYTHON3">Python 3</option>
                    <option value="C">C</option>
                    <option value="CPP">C++</option>
                </Input>
            </FormGroup>
            <FormGroup>
                <Label for="code">Код программы:</Label>
                <Input type="textarea" name="code" bind:value={attempt.code}/>
            </FormGroup>
            <Button on:click={submitAttempt} color="primary">Отправить</Button>
        {/if}
    {:else}
        <Spinner color="primary" />
    {/if}
</div>