<script>
    import {onMount} from "svelte";
    import {push} from "svelte-spa-router";
    import axios from "axios";

    onMount(() => {
        const UID = localStorage.getItem("UID");
        if (UID) {
            axios.post("sender/checkUID", {uid: UID})
                .then(r => {
                    if (r.status !== 200) {
                        push("/")
                    }
                })
                .catch(e => {
                    push("/")
                })
        } else {
            push("/");
        }
    })
</script>