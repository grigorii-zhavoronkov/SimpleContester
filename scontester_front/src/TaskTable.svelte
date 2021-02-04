<script>
    let tasks = [
        {id: 1, title: 'a+b'},
        {id: 2, title: 'a+b v2'}
    ]
</script>

<main>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Title</th>
        </tr>
        </thead>
        {#each tasks as task (task.id)}
            <tr>
                <td><a>{task.id}</a></td>
                <td>{task.title}</td>
            </tr>
        {/each}
    </table>
</main>