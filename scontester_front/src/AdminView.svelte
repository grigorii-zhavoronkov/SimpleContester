<script>
    import Logout from "./Logout.svelte";
    import CheckAdminLogin from "./CheckAdminLogin.svelte";
    import Tasks from "./Tasks.svelte";
    import Senders from "./Senders.svelte";

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
        <li><a href="/#/admin/task_attempts/">Посмотреть все попытки для одного задания</a></li>
        <li><a href="/#/admin/sender_attempts/">Посмотреть все попытки для одного отправляющего</a></li>
        <li><span class="line-through">Очистить все попытки</span></li>
    </ul>
    {#if params.page === "tasks"}
        <Tasks isAdmin="true" />
    {:else if params.page === "senders"}
        <Senders />
    {:else if params.page === "task_attempts"}
    {:else if params.page === "sender_attempts"}
    {/if}
</div>