<template>
  <div>
    <Scanner />
  </div>
</template>

<script>
import Scanner from "@/components/customer/scan/Scanner";

export default {
  name: "Scan",
  components: {
    Scanner,
  },
  mounted() {
    this.$store
      .dispatch("checkIfBusinessIDExists", this.$route.params.id)
      .then((response) => {
        var exists = response.exists;
        if (!exists) {
          this.$router.push("/notfound")
        }
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>
