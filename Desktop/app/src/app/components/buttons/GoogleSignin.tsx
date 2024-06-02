import { signIn } from "@/app/utils/auth"
 
export function GoogleSignin() {
  return (
    <form
      action={async () => {
        "use server"
        await signIn("google")
      }}
    >
      <button type="submit">Sign in with Google</button>
    </form>
  )
} 