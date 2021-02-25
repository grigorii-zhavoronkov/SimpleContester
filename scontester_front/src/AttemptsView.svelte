<script>
    import Logout from "./Logout.svelte";
    import BackLink from "./BackLink.svelte";
    import {Table, Spinner} from "sveltestrap";
    import axios from "axios";
    import {onMount} from "svelte";
    import CheckSenderLogin from "./CheckSenderLogin.svelte";

    let attempts = []

    function loadAttempts() {
        axios.get("/attempt/getAttempts")
            .then(function (response) {
                attempts = response.data;
            })
            .catch(function (err) {
                console.log(err);
                attempts = -1;
            })
    }

    function getStatus(attempt) {
        if (attempt.status) {
            switch (attempt.status) {
                case "WRONG_ANSWER":
                case "TIME_LIMIT_EXCEED":
                case "MEMORY_LIMIT_EXCEED":
                case "RUNTIME_EXCEPTION":
                    return `${attempt.status}#${attempt.lastTestNumber}`
                default:
                    return `${attempt.status}`
            }
        }
    }

    onMount(() => loadAttempts())

</script>

<div class="container">
    <CheckSenderLogin />
    <Logout />
    <BackLink />
    {#if attempts.length !== 0}
        <Table bordered hover>
            <thead>
            <tr>
                <td>ID отправки</td>
                <td>Имя отправляющего</td>
                <td>Название задачи</td>
                <td>Статус отправки</td>
            </tr>
            </thead>
            <tbody>
            {#each attempts as attempt}
                <tr>
                    <td>{attempt.id}</td>
                    <td>{attempt.senderName}</td>
                    <td>{attempt.taskTitle}</td>
                    <td>{getStatus(attempt)}</td>
                </tr>
            {/each}
            </tbody>
        </Table>
    {:else if attempts === -1}
        <p>Произиошла ошибка при загрузке</p>
    {:else}
        <Spinner color="primary" />
    {/if}
</div>