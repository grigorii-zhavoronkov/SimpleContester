<script>

    import RestoreUID from './RestoreUID.svelte'
    import Login from "./Login.svelte";
    import CheckAdminLogin from "./CheckAdminLogin.svelte";
    import CheckSenderLogin from "./CheckSenderLogin.svelte";

</script>

<main>
    <CheckAdminLogin />
    <CheckSenderLogin />
    <Login />
    <RestoreUID/>
</main>

<style>

</style>