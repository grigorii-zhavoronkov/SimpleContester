<script>

    import {push} from "svelte-spa-router"
    import {Button} from "sveltestrap"

    function logout() {
        localStorage.removeItem("UID");
        localStorage.removeItem("Password");
        push("/");
    }
</script>

<div id="logout">
    <Button on:click={logout} color="danger" >Выйти</Button>
</div>
