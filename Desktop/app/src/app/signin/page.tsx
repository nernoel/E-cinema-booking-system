import { signIn } from '@/app/utils/auth'
import { Session } from 'inspector'
import { redirect } from 'next/dist/server/api-utils'

// Google Sign in button
export function GoogleSignIn() {
    return (
        <form
      action={async () => {
        "use server"
        await signIn("google" , { redirectTo: "/dashboard" } )
      }}
    >
      <button type="submit">Signin with Google</button>
    </form>
    )
}

export default function SignInPage(){
    return (
        <div>
            <h1>Sign in page</h1>
            <GoogleSignIn />
            {}
            
        </div>
    )
}