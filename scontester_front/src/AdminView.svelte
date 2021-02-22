<script>
    import Logout from "./Logout.svelte";
    import CheckAdminLogin from "./CheckAdminLogin.svelte";
    import Tasks from "./Tasks.svelte";

    export let params = {};
</script>

<div class="container">
    <CheckAdminLogin />
    <Logout />
    <h3>Добро пожаловать в админ-панель</h3>
    <p>В админ-панеле вы можете</p>
    <ul>
        <li><a href="/#/admin/tasks">Добавлять/<span class="line-through">редактировать</span>/удалять задания</a></li>
        <li><a href="/#/admin/senders">Добавлять/удалять отправителей</a></li>
        <li><a href="/#/task_attempts/">Посмотреть все попытки для одного задания</a></li>
        <li><a href="/#/attempts/">Посмотреть все попытки отправлявших задания</a></li>
        <li><span class="line-through">Очистить все попытки</span></li>
    </ul>
    {#if params.page === "tasks"}
        <Tasks isAdmin="true" />
    {:else if params.page === "senders"}
    {:else if params.page === "task_attempts"}
    {:else if params.page === "attempts"}
    {/if}
</div>