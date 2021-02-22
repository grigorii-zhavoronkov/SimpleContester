<script>
    let senders = [
        {UID: 'ABC11', name: 'Syka'},
        {UID: 'AAA11', name: 'Blyat'}
    ]
</script>

<main>
    <table>
        <thead>
        <tr>
            <td>UID</td>
            <td>Name</td>
        </tr>
        </thead>
        <tbody>
        {#each senders as sender}
            <tr>
                <td>{sender.uid}</td>
                <td>{sender.name}</td>
            </tr>
        {/each}
        </tbody>
    </table>
</main>