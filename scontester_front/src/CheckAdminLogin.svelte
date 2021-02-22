<script>
    import {onMount} from "svelte";
    import {push} from "svelte-spa-router";
    import axios from "axios";

    onMount(() => {
        const password = localStorage.getItem("password");
        const UID = localStorage.getItem("UID");
        if (password) {
            axios.get("admin/api/login", {
                headers: {
                    "Authorization": password
                }
            })
            .then(r => {
                if (r.status !== 200) {
                    push("/")
                }
            })
            .catch(e => {
                push("/")
            })
        } else if (UID) {

        } else {
            push("/");
        }
    })
</script>