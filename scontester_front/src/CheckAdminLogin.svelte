<script>
    import {onMount} from "svelte";
    import {push} from "svelte-spa-router";
    import axios from "axios";

    onMount(() => {
        const password = localStorage.getItem("password");
        if (password) {
            axios.get("admin/api/login", {
                headers: {
                    "Authorization": password
                }
            })
            .then(r => {
                if (r.status !== 200) {
                    push("/");
                }
            })
            .catch(e => {
                push("/");
                console.log(e);
            })
        } else {
            console.log("NO PASS");
            push("/");
        }
    })
</script>