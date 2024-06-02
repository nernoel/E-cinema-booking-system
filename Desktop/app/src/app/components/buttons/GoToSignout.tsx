'use client'

import { MouseEventHandler } from "react";
import { useRouter } from "next/navigation";

export default function SignOutButton() {
  const router = useRouter();

  const goToSignOut: MouseEventHandler<HTMLButtonElement> = (event) => {
    router.push("/api/auth/signout"); 
  };

  return <button onClick={goToSignOut}>Sign out</button>;
}
