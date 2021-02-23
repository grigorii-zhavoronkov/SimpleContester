<script>
    import {pop} from "svelte-spa-router"
</script>

<div class="link" on:click={() => pop()}>Назад</div>

<style>
    .link {
        color: blue;
        cursor: pointer;
    }

    .link:hover {
        text-decoration: underline;
    }
</style>