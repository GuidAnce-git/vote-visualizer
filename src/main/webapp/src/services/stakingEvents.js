export function getStakingEvents() {
    return fetch('http://localhost:8080/test/staking')
        .then(data => data.json())
}