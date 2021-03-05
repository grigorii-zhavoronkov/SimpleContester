<script>
    import Router from 'svelte-spa-router';
    import Home from './Home.svelte';
    import NotFound from './NotFound.svelte';
    import Tasks from './Tasks.svelte';
    import TaskView from './TaskView.svelte';
    import AttemptsView from './AttemptsView.svelte';
    import AdminView from './AdminView.svelte';
    import axios from 'axios';

    const routes = {
        '/': Home,
        '/task/:id': TaskView,
        '/tasks': Tasks,
        '/attempts': AttemptsView,
        '/admin/:page?/:id?': AdminView,
        '*': NotFound
    }

    axios.defaults.baseURL = "http://127.0.0.1:8080"
    axios.defaults.headers.common["Content-Type"]="Application/json"
    axios.defaults.headers.common["Accept"]="Application/json"
    axios.defaults.headers.common["Access-Control-Allow-Origin"]="*"
</script>

<body>
    <Router {routes}/>
</body>